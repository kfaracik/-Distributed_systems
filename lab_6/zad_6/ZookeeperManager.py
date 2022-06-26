import threading
from time import sleep
from kazoo.client import KazooClient

from ProgramManager import ProgramManager


class ZookeeperManager:
    def __init__(self, program_name):
        self.app_name = program_name
        self.z_node_path = "/z"
        self.server = '127.0.0.1:2181'
        self.zookeeper = None
        self.watched_node_map = {}
        self.old_watched_node_map = {}
        self.init = True
        self.updating = False
        self.is_stopped = True
        self.programManager = ProgramManager(program_name)

    def start(self):
        self.zookeeper = KazooClient(hosts=self.server)
        self.zookeeper.start()
        print(f'Connected to server {self.server}')

        self.watch_node(self.z_node_path)

        thread = threading.Thread(target=self.listen_z_node_exists, args=())
        thread.start()

        self.listen_input()

    def show    _tree(self, children, path, depth=1):
        for child in children:
            for i in range(depth):
                print('\t', end='')
            print(f'•{child}')

            new_child_path = path + "/" + child
            child_children = self.zookeeper.get_children(new_child_path)
            if child_children:
                self.show_tree(child_children, new_child_path, depth + 1)

    def show_watched_nodes(self):
        print(self.old_watched_node_map)

    def watch_node(self, path):
        @self.zookeeper.ChildrenWatch(path)
        def watch_children(children):
            if not self.updating:
                if self.init:
                    print(f'\"{path}\" children are now: {len(children)}')
                else:
                    self.update_watched_nodes()
                    print(f'\"{path}\" children are now: {len(children)},'
                          f' z-node children are: {len(self.old_watched_node_map)}')
                if self.old_watched_node_map == {}:
                    self.show_tree(children, path)

    def update_watched_nodes(self):
        self.watched_node_map = {}
        self.updating = True
        self.update_nodes(self.z_node_path)
        self.old_watched_node_map = self.watched_node_map
        self.updating = False

    def update_nodes(self, path):
        children = self.zookeeper.get_children(path)
        for child in children:
            new_child_path = path + "/" + child
            if new_child_path not in self.watched_node_map:
                self.watched_node_map.update({new_child_path: True})
                self.watch_node(new_child_path)
            child_children = self.zookeeper.get_children(new_child_path)
            if child_children:
                self.update_nodes(new_child_path)

    def listen_input(self):
        sleep(2)
        self.init = False

        if self.exists_z_node():
            self.update_watched_nodes()

        while 1:
            command = input(">> ")

            if not self.exists_z_node():
                print("Z-node does not exist!")
                continue
            else:
                self.update_watched_nodes()

            if command == "exit":
                exit(0)
            elif command == "ls":
                children = self.zookeeper.get_children(self.z_node_path)
                self.show_tree(children, self.z_node_path)
            elif command == "watchers":
                self.show_watched_nodes()
            else:
                print("Try [exit, ls, watchers]")

    def exists_z_node(self):
        if self.zookeeper.exists(self.z_node_path):
            return True
        else:
            return False

    def listen_z_node_exists(self):
        while 1:
            if not self.exists_z_node() and not self.is_stopped:
                self.is_stopped = True
                self.programManager.stop()
            elif self.exists_z_node() and self.is_stopped:
                self.watch_node(self.z_node_path)
                self.update_watched_nodes()
                self.programManager.start()
                self.is_stopped = False
            sleep(2)

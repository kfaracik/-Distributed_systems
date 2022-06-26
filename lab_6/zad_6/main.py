from ZookeeperManager import ZookeeperManager


def start():
    zookeeperManager = ZookeeperManager("mspaint.exe")
    zookeeperManager.start()


if __name__ == '__main__':
    start()

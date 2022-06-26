import subprocess


class ProgramManager:
    def __init__(self, app_name):
        self.app_name = app_name
        self.app = None

    def start(self):
        self.app = subprocess.Popen([self.app_name])

    def stop(self):
        self.app.kill()
        self.app.terminate()


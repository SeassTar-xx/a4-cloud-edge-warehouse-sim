"""AGV controller adapter placeholder."""


class AGVControllerAdapter:
    """Adapter boundary for AGV state and commands."""

    def encode_command(self, agv_id, path):
        # TODO: Replace this dict with Yihui platform command schema.
        return {"agv_id": agv_id, "path": path, "command": "FOLLOW_PATH"}

    def decode_state(self, platform_state):
        # TODO: Convert real platform AGV state into a4_core.model.AGV.
        return platform_state


if __name__ == "__main__":
    print("agv_controller adapter placeholder; no external command is sent.")


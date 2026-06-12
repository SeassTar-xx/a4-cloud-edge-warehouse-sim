"""Slot manager adapter placeholder."""

from a4_core.invariants import check_state


class SlotManagerAdapter:
    """Adapter boundary for shelf slots and inventory mapping."""

    def validate_inventory(self, state):
        # TODO: Compare against platform inventory API after the real API is known.
        return check_state(state)

    def export_inventory(self, inventory):
        return [{"cargo_id": cid, "shelf_id": shelf_id, "slot_id": slot_id} for cid, (shelf_id, slot_id) in inventory.items()]


if __name__ == "__main__":
    print("slot_manager adapter placeholder; local inventory export only.")


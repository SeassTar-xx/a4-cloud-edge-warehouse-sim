"""Handoff manager adapter placeholder."""


class HandoffManagerAdapter:
    """Adapter boundary for handoff capacity and occupancy."""

    def can_accept(self, zone, cargos):
        # TODO: Bind to platform handoff occupancy stream when available.
        return zone.can_accept(cargos)

    def accept(self, zone, cargos):
        zone.accept(cargos)
        return {"zone_id": zone.id, "used_volume": zone.used_volume}


if __name__ == "__main__":
    print("handoff_manager adapter placeholder; local capacity checks only.")


    for (gk p : sock.drain()) { if (p instanceof mt && ((mt) p).a == 4) gotItem = true; if (p instanceof bu) collected = true; }
    check(gotItem && collected, "guest picks up a dropped item (inventory add + collect animation)");

    // disconnect
    sock.fireClose();
    srv.tickForTest();

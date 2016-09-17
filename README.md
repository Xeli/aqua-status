# AquaStatus

This is a nodejs project made with [LoopBack](https://loopback.io/) to show statistics gather by [AquariumPi](https://github.com/Xeli/aquariumpi)

# How it works
A raspberry pi running [AquariumPi](https://github.com/Xeli/aquariumpi) gathers the data and sends it to this AquaStatus.

AquaStatus stores it in a MySQL database. Next it sends the new data to each client connected via websockets.

Each client / browser get's a html page with svg's showing various metrics of the reef tank such as:

* Temperature
* pH
* Light intensity in percentage
* and more

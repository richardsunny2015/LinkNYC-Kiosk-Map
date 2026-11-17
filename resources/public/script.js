import { LeafletMap, TileLayer, Marker, Icon } from 'leaflet';

const map = new LeafletMap('map').setView([40.7144444444, -74.0002777778], 12);

new TileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);

const displayLinksOnMap = links => {
    links.forEach(link => {
        const lat = parseFloat(link.latitude);
        const longitude = parseFloat(link.longitude);
        const iconUrl = link.status == "Repair"
            ? "/images/wifi_secured_repair.png"
            : "/images/wifi_secured_active.png"
        const icon = new Icon({
            iconUrl,
            iconSize: [32, 57], // size of the icon
            popupAnchor: [-3, -76] // point from which the popup should open relative to the iconAnchor
        })
        new Marker([lat, longitude], {icon})
            .addTo(map)
            .bindPopup(`status: ${link.status}<br>address: ${link.address}`);
    })
}

const data = fetch("/links")
    .then(response => response.json())
    .then(displayLinksOnMap);


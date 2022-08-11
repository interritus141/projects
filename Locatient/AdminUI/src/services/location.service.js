import axios from "axios";

const base = "https://api-dot-nhsproject-342615.nw.r.appspot.com";
const API_LOCATION_ADD = base.concat("/api/admin/addLocation");
const API_LOCATION_DELETE = base.concat("/api/admin/removeLocation");
const API_LOCATION_GET = base.concat("/api/data/location");

class LocationService {
  addLocation(roomName, level, jwt) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.post(
      API_LOCATION_ADD,
      {
        roomName,
        level,
      },
      config
    );
  }

  deleteLocation(roomName, level, jwt) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.post(
      API_LOCATION_DELETE,
      {
        roomName,
        level,
      },
      config
    );
  }

  getLocations(jwt, limit = 0) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.get(API_LOCATION_GET, config);
  }
}

export default new LocationService();

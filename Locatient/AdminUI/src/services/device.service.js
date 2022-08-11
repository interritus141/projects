import axios from "axios";
const base = "https://api-dot-nhsproject-342615.nw.r.appspot.com";
const API_DEVICE_ADD = base.concat("/api/admin/addDevice");
const API_DEVICE_DELETE = base.concat("/api/admin/removeDevice");
const API_DEVICE_GET = base.concat("/api/data/device");

class DeviceService {
  addDevice(name, apiKey, jwt) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.post(
      API_DEVICE_ADD,
      {
        name,
        apiKey,
      },
      config
    );
  }

  deleteDevice(name, apiKey, jwt) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.post(
      API_DEVICE_DELETE,
      {
        name,
        apiKey,
      },
      config
    );
  }

  getDevices(jwt, limit = 0) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.get(API_DEVICE_GET, config);
  }
}

export default new DeviceService();

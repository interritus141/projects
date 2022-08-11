import axios from "axios";

const base = "https://api-dot-nhsproject-342615.nw.r.appspot.com";
const JWT_URL = base.concat("/api/auth/login");
const ADD_USER_URL = base.concat("/api/admin/register");
const DELETE_USER_URL = base.concat("/api/admin/unregister");
const GET_USER_URL = base.concat("/api/data/user");
const GET_QR_CODE = base.concat("/api/admin/getQRCode?nhsid=");

class AuthService {
  getJwt(nhsid, password) {
    return axios.post(JWT_URL, {
      nhsid,
      password,
    });
  }

  registerUser(nhsid, password, privilege, jwt) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.post(
      ADD_USER_URL,
      {
        nhsid,
        password,
        privilege,
      },
      config
    );
  }

  deleteUser(nhsid, jwt) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.post(
      DELETE_USER_URL,
      {
        nhsid,
        type: "user",
      },
      config
    );
  }

  getUsers(jwt, limit = 0) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.get(GET_USER_URL, config);
  }

  getQRCode(nhsid, jwt) {
    const config = {
      headers: { Authorization: `Bearer ${jwt}` },
    };
    return axios.get(GET_QR_CODE.concat(nhsid), config);
  }
}

export default new AuthService();

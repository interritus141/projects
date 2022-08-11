import axios from "axios";

const base = "https://api-dot-nhsproject-342615.nw.r.appspot.com";
const JWT_URL = base.concat("/api/auth/login");

class AuthService {
  getJwt(nhsid, password) {
    return axios.post(JWT_URL, {
      nhsid,
      password,
    });
  }
}

export default new AuthService();

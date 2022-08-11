import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import AccessibleIcon from "@mui/icons-material/Accessible";
import AddchartIcon from "@mui/icons-material/Addchart";
import AutoGraphIcon from "@mui/icons-material/AutoGraph";
import ArticleIcon from "@mui/icons-material/Article";
import AccessibleForwardIcon from "@mui/icons-material/AccessibleForward";
import NotListedLocationIcon from "@mui/icons-material/NotListedLocation";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import FavoriteIcon from "@mui/icons-material/Favorite";
import LogoutIcon from "@mui/icons-material/Logout";
import { jwtState } from "../atom";
import { useRecoilState } from "recoil";

const Item = ({ name, icon, path, onClick }) => {
  const location = useLocation();
  const navigate = useNavigate();

  if (!onClick) {
    onClick = () => navigate(path);
  }
  return (
    <div
      className={"item ".concat(location.pathname === path ? "current" : "")}
      onClick={onClick}
    >
      <div className="shape"></div>
      {icon}
      <div className="text">{name}</div>
    </div>
  );
};

const Sidebar = ({ toggle }) => {
  const [jwt, setJwt] = useRecoilState(jwtState);
  const logout = () => {
    setJwt("");
    localStorage.removeItem("jwt");
  };
  return (
    <div className={`sidebar toggled ${toggle}`}>
      <div className="head">
        <img className="title" src="/imgs/Locatient(2).png" alt="" />
      </div>
      <div className="nav">
        <div className="category">Common</div>
        <Item name="Dashboard" path="/" icon={<AddchartIcon />} />
        <Item name="Analytics" path="/analytics" icon={<AutoGraphIcon />} />
        <Item name="Logs" path="/logs" icon={<ArticleIcon />} />
        <div className="category">Favorites</div>
        <Item
          name="Patient Activities"
          path="/patient_activities"
          icon={<AccessibleForwardIcon />}
        />
        <Item
          name="Location Activities"
          path="/location_activities"
          icon={<NotListedLocationIcon />}
        />
        <Item
          name="Manage Favorites"
          path="/favorites"
          icon={<FavoriteIcon />}
        />
        <div className="category">Select</div>
        <Item name="Patients" path="/patients" icon={<AccessibleIcon />} />
        <Item name="Locations" path="/locations" icon={<LocationOnIcon />} />
        <div className="category">Others</div>
        <Item
          name="Logout"
          path="/logout"
          icon={<LogoutIcon />}
          onClick={logout}
        />
      </div>
    </div>
  );
};

export default Sidebar;

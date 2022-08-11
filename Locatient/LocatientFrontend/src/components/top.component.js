import React, { useState, useEffect } from "react";
import NotificationsNoneIcon from "@mui/icons-material/NotificationsNone";
import Avatar from "@mui/material/Avatar";
import { deepOrange } from "@mui/material/colors";
import { userState } from "../atom";
import { useRecoilState } from "recoil";
import Badge from "@mui/material/Badge";
import Chip from "@mui/material/Chip";

const Top = () => {
  const [time, setTime] = useState(null);
  const [user, setUser] = useRecoilState(userState);

  useEffect(() => {
    function update() {
      const now = new Date();
      setTime(
        `${now.toLocaleDateString()} - ${now.toLocaleTimeString([], {
          hour: "2-digit",
          minute: "2-digit",
        })}`
      );
    }
    update();
    const interval = setInterval(() => update(), 500);
    return () => {
      clearInterval(interval);
    };
  }, []);
  return (
    <div className="top">
      <div className="time">{time}</div>
      <div className="notifications">
        <div className="status">
          {user && user.privilage
            ? user.privilage.toUpperCase()
            : "NO PRIVILAGE"}
        </div>
        <div className="bell">
          <Badge color="secondary" badgeContent={3}>
            <NotificationsNoneIcon />
          </Badge>
        </div>
        <div className="avatar">
          <Chip
            avatar={
              <Avatar sx={{ bgcolor: deepOrange[500] }}>
                {user && user.nhsid ? user.nhsid.charAt(0).toUpperCase() : "!"}
              </Avatar>
            }
            label={user && user.nhsid ? user.nhsid : ""}
            variant="outlined"
          />
        </div>
      </div>
    </div>
  );
};

export default Top;

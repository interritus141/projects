import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import {
  favLocationState,
  favPatientState,
  locationDataState,
  logDataState,
} from "../atom";
import { patientDataState } from "../atom";

import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  BarElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Line, Bar } from "react-chartjs-2";
import Clock from "react-clock";
import "react-clock/dist/Clock.css";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  BarElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);
const BarLocation = () => {
  const [logs, setLogs] = useRecoilState(logDataState);
  const [locations, setLocations] = useRecoilState(locationDataState);
  const [favoriteLocations, setFavoriteLocations] =
    useRecoilState(favLocationState);
  const [label, setLabel] = useState([]);
  const [data, setData] = useState([]);

  useEffect(() => {
    let favorites = Array.from(favoriteLocations);
    let favoriteCount = {};
    for (let i = 0; i < favorites.length; i++) {
      favoriteCount[favorites[i]] = 0;
    }
    for (let i = 0; i < logs.logs.length; i++) {
      let keyLocation = JSON.stringify({
        roomName: logs.logs[i].roomName,
        level: logs.logs[i].level,
      });
      if (keyLocation in favoriteCount) {
        favoriteCount[keyLocation] += 1;
      }
    }
    let tempLabel = [...Array(favorites.length)];
    let tempData = [...Array(favorites.length)];
    for (let i = 0; i < favorites.length; i++) {
      let row = JSON.parse(favorites[i]);
      tempLabel[i] = `${row.level} ${row.roomName}`;
      tempData[i] = favoriteCount[favorites[i]];
    }
    setLabel(tempLabel);
    setData(tempData);
  }, [logs, favoriteLocations, locations]);

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: "top",
      },
      title: {
        display: true,
        text: "Favorite Location Activity Graph",
      },
    },
  };

  const tableData = {
    labels: label,
    datasets: [
      {
        label: "Favorite Locations",
        data: data,
        backgroundColor: "rgba(255, 99, 132, 0.5)",
      },
    ],
  };
  // return <Line options={options} data={chartdata} />;
  return <Bar options={options} data={tableData} />;
};

const DayGraph = () => {
  const [logs, setLogs] = useRecoilState(logDataState);
  const [pastDays, setPastDays] = useState([]);
  const [data, setData] = useState([]);
  useEffect(() => {
    let dayArr = [];
    let date = new Date();
    for (let days = 0; days < 7; days++) {
      let last = new Date(date.getTime() - days * 24 * 60 * 60 * 1000);
      let day = last.getDate();
      let month = last.getMonth() + 1;
      dayArr = [`${month}/${day}`, ...dayArr];
    }
    setPastDays(dayArr);
    let tempData = [0, 0, 0, 0, 0, 0, 0];
    const miliInDay = 1000 * 60 * 60 * 24;
    for (let i = 0; i < logs.logs.length; i++) {
      let loggingTime = new Date(Date.parse(logs.logs[i].loggingTime));
      let diff = Math.floor(
        Math.floor(date.getTime() - loggingTime.getTime()) / miliInDay
      );

      if (diff >= 7) {
        break;
      }

      let index = 6 - diff;
      tempData[index] += 1;
      console.log(diff);
    }
    setData(tempData);
    console.log("sep");
  }, [logs]);

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: "top",
      },
      title: {
        display: true,
        text: "Logging Activity",
      },
    },
  };
  const chartdata = {
    labels: pastDays,
    datasets: [
      {
        label: "Log Frequency",
        data: data,
        borderColor: "rgb(255, 99, 132)",
        backgroundColor: "rgba(255, 99, 132, 0.5)",
      },
    ],
  };
  return <Line options={options} data={chartdata} />;
};

const TotalPatient = () => {
  const [patients, setPatients] = useRecoilState(patientDataState);

  return (
    <div className="item i1">
      <div className="stats-card-1">
        <div className="title">Active Patients</div>
        <div className="stats">{patients.patients.length}</div>
      </div>
    </div>
  );
};

const FollowedPatient = () => {
  const [favoritePatients, setFavoritePatients] =
    useRecoilState(favPatientState);

  return (
    <div className="item i2">
      <div className="stats-card-1">
        <div className="title">Followed Patients</div>
        <div className="stats">{favoritePatients.size}</div>
      </div>
    </div>
  );
};

const LogsToday = () => {
  const [logs, setLogs] = useRecoilState(logDataState);
  const [logsToday, setLogsToday] = useState(0);
  useEffect(() => {
    let date = new Date();
    let count = 0;
    for (let i = 0; i < logs.logs.length; i++) {
      let loggingTime = new Date(Date.parse(logs.logs[i].loggingTime));
      let diff = date.getDay() - loggingTime.getDay();
      if (diff > 1) {
        break;
      } else {
        count += 1;
      }
    }
    setLogsToday(count);
  }, [logs]);
  return (
    <div className="item i3">
      <div className="stats-card-1">
        <div className="title">Daily Logs</div>
        <div className="stats">{logsToday}</div>
      </div>
    </div>
  );
};

const MonthlyLogs = () => {
  const [logs, setLogs] = useRecoilState(logDataState);

  return (
    <div className="item i4">
      <div className="stats-card-1">
        <div className="title">Monthly Logs</div>
        <div className="stats">{logs.logs.length}</div>
      </div>
    </div>
  );
};

const Home = () => {
  const [clock, setClock] = useState(new Date());

  useEffect(() => {
    const interval = setInterval(() => setClock(new Date()), 1000);

    return () => {
      clearInterval(interval);
    };
  }, []);
  return (
    <div className="dashboard">
      <div className="clamp">
        <div className="single">
          <Clock value={clock} />
        </div>
      </div>
      <div className="spacing"></div>

      <div className="title-clamp">
        <div className="page-title">Dashboard</div>
      </div>

      <div className="quad">
        <TotalPatient />
        <FollowedPatient />
        <LogsToday />
        <MonthlyLogs />
      </div>
      <div className="double">
        <div className="item">
          <DayGraph />
        </div>
        <div className="item">
          <BarLocation />
        </div>
      </div>
    </div>
  );
};

export default Home;

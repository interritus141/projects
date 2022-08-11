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
  ArcElement,
  LinearScale,
  PointElement,
  BarElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Line, Bar, Pie } from "react-chartjs-2";

ChartJS.register(
  CategoryScale,
  ArcElement,
  LinearScale,
  PointElement,
  BarElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

export const labels = [];

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
    }
    setData(tempData);
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

const LocationPie = () => {
  const [logs, setLogs] = useRecoilState(logDataState);
  const [label, setLabel] = useState([]);
  const [data, setData] = useState([]);
  const [color, setColor] = useState([]);

  useEffect(() => {
    let levelIndices = {};
    let objLen = 0;
    for (let i = 0; i < logs.logs.length; i++) {
      let level = logs.logs[i].level;
      if (!(level in levelIndices)) {
        levelIndices[level] = 1;
        objLen++;
      } else {
        levelIndices[level]++;
      }
    }
    let tempLabel = [...Array(objLen)];
    let tempData = [...Array(objLen)];
    let floors = Object.keys(levelIndices);
    for (let i = 0; i < objLen; i++) {
      tempLabel[i] = `Floor ${floors[i]}`;
      tempData[i] = levelIndices[floors[i]];
    }

    let tempColors = [];
    let presets = [
      "#26A69A",
      "#66BB6A",
      "#9CCC65",
      "#9CCC65",
      "#42A5F5",
      "#26C6DA",
    ];
    for (let i = 0; i < objLen; i++) {
      tempColors.push(presets[i % 6]);
    }
    setColor(tempColors);
    setLabel(tempLabel);
    setData(tempData);
  }, [logs]);

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: "top",
      },
      title: {
        display: true,
        text: "Location Activity Spread",
      },
    },
  };

  const tableData = {
    labels: label,
    datasets: [
      {
        label: "Activities",
        data: data,
        backgroundColor: color,
        borderColor: color,
        borderWidth: 2,
      },
    ],
  };
  // return <Line options={options} data={chartdata} />;
  return <Pie options={options} data={tableData} />;
};

const FavLocationPie = () => {
  const [logs, setLogs] = useRecoilState(logDataState);
  const [label, setLabel] = useState([]);
  const [data, setData] = useState([]);
  const [favoriteLocations, setFavoriteLocations] =
    useRecoilState(favLocationState);
  const [color, setColor] = useState([]);

  useEffect(() => {
    let favorites = Array.from(favoriteLocations);
    let levelIndices = {};
    let objLen = 0;
    for (let i = 0; i < logs.logs.length; i++) {
      let keyLocation = JSON.stringify({
        roomName: logs.logs[i].roomName,
        level: logs.logs[i].level,
      });
      if (favorites.includes(keyLocation)) {
        let level = logs.logs[i].level;
        if (!(level in levelIndices)) {
          levelIndices[level] = 1;
          objLen++;
        } else {
          levelIndices[level]++;
        }
      }
    }
    let tempLabel = [...Array(objLen)];
    let tempData = [...Array(objLen)];
    let floors = Object.keys(levelIndices);
    for (let i = 0; i < objLen; i++) {
      tempLabel[i] = `Floor ${floors[i]}`;
      tempData[i] = levelIndices[floors[i]];
    }

    let tempColors = [];
    let presets = [
      "#FFCA28",
      "#FFA726",
      "#FF7043",
      "#795548",
      "#BDBDBD",
      "#78909C",
    ];
    for (let i = 0; i < objLen; i++) {
      tempColors.push(presets[i % 6]);
    }
    setColor(tempColors);
    setLabel(tempLabel);
    setData(tempData);
  }, [logs]);

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: "top",
      },
      title: {
        display: true,
        text: "Favorite Location Activity Spread",
      },
    },
  };

  const tableData = {
    labels: label,
    datasets: [
      {
        label: "Activities",
        data: data,
        backgroundColor: color,
        borderColor: color,
        borderWidth: 2,
      },
    ],
  };
  // return <Line options={options} data={chartdata} />;
  return <Pie options={options} data={tableData} />;
};

const Analytics = () => {
  return (
    <div>
      <div className="title-clamp">
        <div className="page-title">Analytics</div>
      </div>
      <div className="spacing"></div>
      <div className="double">
        <div className="item">
          <DayGraph />
        </div>
        <div className="item">
          <BarLocation />
        </div>
      </div>
      <div className="double">
        <div className="item">
          <LocationPie />
        </div>
        <div className="item">
          <FavLocationPie />
        </div>
      </div>
      <div className="spacing"></div>

      <div className="roomFrequency"></div>
      <div className="avearageScanFrequency"></div>
    </div>
  );
};

export default Analytics;

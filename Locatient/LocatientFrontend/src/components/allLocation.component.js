import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { favLocationState, locationDataState } from "../atom";
import { DataGrid, GridToolbar } from "@mui/x-data-grid";
import Button from "@mui/material/Button";
import StarIcon from "@mui/icons-material/Star";
import { yellow, grey } from "@mui/material/colors";
import Clock from "react-clock";
import "react-clock/dist/Clock.css";

const Locations = () => {
  const [locations, setLocations] = useRecoilState(locationDataState);
  const [rows, setRows] = useState([]);
  const [columns, setColumns] = useState([]);
  const [selection, setSelection] = useState([]);
  const [favoriteLocations, setFavoriteLocations] =
    useRecoilState(favLocationState);
  useEffect(() => {
    if (locations && locations.locations) {
      const tempRows = locations.locations.map((row, index) => {
        return {
          id: index,
          roomName: row.roomName,
          level: row.level,
          favorited: favoriteLocations.has(JSON.stringify(row)),
        };
      });
      const tempColumns = [
        { field: "roomName", headerName: "Room Name", minWidth: 160, flex: 1 },
        { field: "level", headerName: "Floor", minWidth: 50, flex: 1 },
        {
          field: "favorited",
          headerName: "Favorite",
          minWidth: 120,
          flex: 1,
          renderCell: (params) => {
            return (
              <div>
                {params.value ? (
                  <StarIcon sx={{ color: yellow[800] }} />
                ) : (
                  <StarIcon sx={{ color: grey[600] }} />
                )}
              </div>
            );
          },
        },
      ];
      setRows(tempRows);
      setColumns(tempColumns);
    }
  }, [locations, favoriteLocations]);

  const [clock, setClock] = useState(new Date());

  useEffect(() => {
    const interval = setInterval(() => setClock(new Date()), 1000);

    return () => {
      clearInterval(interval);
    };
  }, []);

  return (
    <div>
      <div className="clamp">
        <div className="single">
          <Clock value={clock} />
        </div>
      </div>
      <div className="spacing"></div>
      <div className="title-clamp">
        <div className="page-title">Available Locations</div>
      </div>
      <div className="spacing"></div>
      <div className="single">
        <DataGrid
          autoHeight
          checkboxSelection={true}
          rows={rows}
          columns={columns}
          onSelectionModelChange={(newSelection) => {
            setSelection(newSelection);
          }}
          components={{ Toolbar: GridToolbar }}
        />
        <div className="spacing"></div>
        <Button
          variant="contained"
          onClick={() => {
            let selected = selection.map((index) => {
              return locations.locations[index];
            });
            let news = new Set();

            for (let i = 0; i < selected.length; i++) {
              if (!favoriteLocations.has(selected[i])) {
                news.add(selected[i]);
              }
            }
            let tostr = (d) => {
              if (typeof d === "object") {
                return JSON.stringify(d);
              } else {
                return d;
              }
            };
            setFavoriteLocations(
              new Set([
                ...Array.from(favoriteLocations).map(tostr),
                ...Array.from(news).map(tostr),
              ])
            );
            localStorage.setItem(
              "favoriteLocations",
              JSON.stringify(
                Array.from(
                  new Set([
                    ...Array.from(favoriteLocations).map(tostr),
                    ...Array.from(news).map(tostr),
                  ])
                )
              )
            );
          }}
        >
          Favorite
        </Button>
      </div>
      <div className="spacing"></div>
    </div>
  );
};

export default Locations;

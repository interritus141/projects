import React, { useState, useEffect } from "react";
import { DataGrid, GridToolbar } from "@mui/x-data-grid";
import { favLocationState, favPatientState } from "../atom";
import { useRecoilState } from "recoil";
import Button from "@mui/material/Button";
const Favorites = () => {
  const [favoriteLocations, setFavoriteLocations] =
    useRecoilState(favLocationState);
  const [favoritePatients, setFavoritePatients] =
    useRecoilState(favPatientState);
  const [rowsp, setRowsp] = useState([]);
  const [columnsp, setColumnsp] = useState([]);
  const [selectionp, setSelectionp] = useState([]);
  const [rowsl, setRowsl] = useState([]);
  const [columnsl, setColumnsl] = useState([]);
  const [selectionl, setSelectionl] = useState([]);
  useEffect(() => {
    const tempRows = [...favoritePatients].map((rowJSON, index) => {
      let row = JSON.parse(rowJSON);
      return {
        id: index,
        nhsid: row.nhsid,
        firstname: row.firstname,
        lastname: row.lastname,
      };
    });
    const tempColumns = [
      { field: "nhsid", headerName: "ID", width: 150, flex: 1 },
      { field: "firstname", headerName: "Firstname", width: 150, flex: 1 },
      { field: "lastname", headerName: "Lastname", width: 150, flex: 1 },
    ];
    setRowsp(tempRows);
    setColumnsp(tempColumns);
  }, [favoritePatients]);
  useEffect(() => {
    const tempRows = [...favoriteLocations].map((rowJSON, index) => {
      let row = JSON.parse(rowJSON);
      return {
        id: index,
        roomName: row.roomName,
        level: row.level,
      };
    });
    const tempColumns = [
      { field: "roomName", headerName: "Room Name", width: 150, flex: 1 },
      { field: "level", headerName: "Floor", width: 150, flex: 1 },
    ];
    setRowsl(tempRows);
    setColumnsl(tempColumns);
  }, [favoriteLocations]);

  return (
    <div className="favorites">
      <div className="spacing"></div>
      <div className="title-clamp">
        <div className="page-title">Manage Favorites</div>
      </div>
      <div className="spacing"></div>
      <div className="double">
        <div className="item">
          <div className="graph-title">Favorite Patients</div>
          <DataGrid
            autoHeight
            rows={rowsp}
            columns={columnsp}
            onSelectionModelChange={(newSelection) => {
              setSelectionp(newSelection);
            }}
            components={{ Toolbar: GridToolbar }}
          />
          <div className="spacing"></div>
          <Button
            variant="contained"
            onClick={() => {
              let selected = selectionp[0];

              let tostr = (d) => {
                if (typeof d === "object") {
                  return JSON.stringify(d);
                } else {
                  return d;
                }
              };
              setFavoritePatients(
                new Set([
                  ...Array.from(favoritePatients).filter((element, index) => {
                    return index !== selected;
                  }),
                ])
              );
              localStorage.setItem(
                "favoritePatients",
                JSON.stringify(
                  Array.from(
                    new Set([
                      ...Array.from(favoritePatients).filter(
                        (element, index) => {
                          return index !== selected;
                        }
                      ),
                    ])
                  )
                )
              );
            }}
          >
            UnFavorite
          </Button>
        </div>
        <div className="item">
          <div className="graph-title">Favorite Locations</div>
          <DataGrid
            autoHeight
            rows={rowsl}
            columns={columnsl}
            onSelectionModelChange={(newSelection) => {
              setSelectionl(newSelection);
            }}
            components={{ Toolbar: GridToolbar }}
          />
          <div className="spacing"></div>
          <Button
            variant="contained"
            onClick={() => {
              let selected = selectionl[0];

              let tostr = (d) => {
                if (typeof d === "object") {
                  return JSON.stringify(d);
                } else {
                  return d;
                }
              };
              setFavoriteLocations(
                new Set([
                  ...Array.from(favoriteLocations).filter((element, index) => {
                    return index !== selected;
                  }),
                ])
              );
              localStorage.setItem(
                "favoriteLocations",
                JSON.stringify(
                  Array.from(
                    new Set([
                      ...Array.from(favoriteLocations).filter(
                        (element, index) => {
                          return index !== selected;
                        }
                      ),
                    ])
                  )
                )
              );
            }}
          >
            UnFavorite
          </Button>
        </div>
      </div>
    </div>
  );
};

export default Favorites;

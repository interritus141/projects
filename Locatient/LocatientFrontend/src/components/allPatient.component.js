import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { favPatientState, patientDataState } from "../atom";
import { DataGrid, GridToolbar } from "@mui/x-data-grid";
import Button from "@mui/material/Button";
import StarIcon from "@mui/icons-material/Star";
import { yellow, grey } from "@mui/material/colors";
import Clock from "react-clock";
import "react-clock/dist/Clock.css";

const Patients = () => {
  const [patients, setPatients] = useRecoilState(patientDataState);
  const [rows, setRows] = useState([]);
  const [columns, setColumns] = useState([]);
  const [selection, setSelection] = useState([]);
  const [favoritePatients, setFavoritePatients] =
    useRecoilState(favPatientState);
  useEffect(() => {
    if (patients && patients.patients) {
      const tempRows = patients.patients.map((row, index) => {
        return {
          id: index,
          nhsid: row.nhsid,
          firstname: row.firstname,
          lastname: row.lastname,
          favorited: favoritePatients.has(JSON.stringify(row)),
        };
      });
      const tempColumns = [
        { field: "nhsid", headerName: "ID", minWidth: 120, flex: 1 },
        { field: "firstname", headerName: "Firstname", minWidth: 120, flex: 1 },
        { field: "lastname", headerName: "Lastname", minWidth: 120, flex: 1 },
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
  }, [patients, favoritePatients]);

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
        <div className="page-title">Available Patients</div>
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
              return patients.patients[index];
            });
            let news = new Set();

            for (let i = 0; i < selected.length; i++) {
              if (!favoritePatients.has(selected[i])) {
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
            setFavoritePatients(
              new Set([
                ...Array.from(favoritePatients).map(tostr),
                ...Array.from(news).map(tostr),
              ])
            );
            localStorage.setItem(
              "favoritePatients",
              JSON.stringify(
                Array.from(
                  new Set([
                    ...Array.from(favoritePatients).map(tostr),
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

export default Patients;

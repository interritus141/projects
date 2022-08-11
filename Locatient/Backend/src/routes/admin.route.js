const router = require("express").Router();
const { Patient, User, Location, Device, Log } = require("../models/model");
const qrcode = require("qrcode");

const {
  patientRegistrationValidation,
  unregistrationValidation,
  registrationValidation,
  deviceValidation,
  locationValidation,
} = require("../validation/validation");

// router.post("/test/registerPatient/", (req, res) => {
//   const { error } = patientRegistrationValidation(req.body);
//   if (error) {
//     return res.status("400").json({ error: error.details[0].message });
//   } else {
//     return res
//       .status("200")
//       .json({ message: "Register patient validated successfully" });
//   }
// });

router.post("/registerPatient/", async (req, res) => {
  const { error } = patientRegistrationValidation(req.body);
  if (error) {
    return res.status("400").json({ error: error.details[0].message });
  } else {
    const patient = await Patient.findOne({ nhsid: req.body.nhsid });
    if (patient) {
      return res
        .status("400")
        .json({ error: "Patient with supplied nhsid already exists" });
    }

    const newPatient = new Patient({
      nhsid: req.body.nhsid,
      firstname: req.body.firstname,
      lastname: req.body.lastname,
    });

    try {
      const savedResult = await newPatient.save();
      return res.status("200").json({
        message: "Patient registered successfully",
        saved: savedResult,
      });
    } catch (error) {
      console.log("!!" + error);
      return res
        .status("500")
        .json({ error: "Registration failed, cannot save patient" });
    }
  }
});

// router.post("/test/register/", async (req, res) => {
//   const { error } = registrationValidation(req.body);
//   if (error) {
//     return res.status("400").json({ error: error.details[0].message });
//   } else {
//     return res
//       .status("200")
//       .json({ message: "Register user validated successfully" });
//   }
// });

router.post("/register/", async (req, res) => {
  const { error } = registrationValidation(req.body);
  if (error) {
    return res.status("400").json({ error: error.details[0].message });
  } else {
    // check if user exists
    const user = await User.findOne({
      nhsid: req.body.nhsid,
    });
    if (user) {
      return res.status("400").json({ error: "User already exists" });
    }

    const newUser = new User({
      nhsid: req.body.nhsid,
      password: req.body.password,
      privilege: req.body.privilege,
    });
    try {
      const savedResult = await newUser.save();
      return res.status(200).json({
        message: "User registered successfully",
        saved: savedResult,
      });
    } catch (error) {
      console.log("!!" + error);
      return res
        .status("500")
        .json({ error: "Registration failed, cannot save user" });
    }
  }
});

router.post("/unregister/", async (req, res) => {
  const { error } = unregistrationValidation(req.body);
  if (error) {
    return res.status("400").json({ error: error.details[0].message });
  } else {
    // check if user exists
    let target;
    if (req.body.type == "user") {
      target = await User.findOne({
        nhsid: req.body.nhsid,
      });
    } else {
      target = await Patient.findOne({
        nhsid: req.body.nhsid,
      });
    }

    if (!target) {
      return res
        .status("400")
        .json({ error: "Target to unregister does not exist" });
    }
    try {
      if (req.body.type == "user") {
        await User.deleteOne({
          nhsid: req.body.nhsid,
        });
        return res.status(200).json({
          message: "User deleted successfully",
        });
      } else {
        const patientsDeleted = await Log.deleteMany({ patient: target._id });
        await Patient.deleteOne({
          nhsid: req.body.nhsid,
        });
        return res.status(200).json({
          message: "User deleted successfully",
          patientsDeleted,
        });
      }
    } catch (error) {
      console.log("!!" + error);
      return res
        .status("500")
        .json({ error: "Deletion failed, cannot delete entity" });
    }
  }
});

router.get("/getQRCode", async (req, res) => {
  const nhsidTar = req.query.nhsid;
  if (!nhsidTar) {
    return res.status("400").json({ error: "Require nhsid to be supplied" });
  } else {
    // check if user exists
    const patient = await Patient.findOne({
      nhsid: nhsidTar,
    });
    if (!patient) {
      return res.status("400").json({ error: "Patient cannot be found." });
    }
    try {
      let code = await qrcode.toDataURL(patient.qrhash);
      res.status("200").json({
        dataUrl: code,
        hash: patient.qrhash,
      });
    } catch (error) {
      console.log(error);
      res
        .status("500")
        .json({ error: "Cannot generate dataUrl", hash: patient.qrhash });
    }
  }
});

router.post("/addLocation/", async (req, res) => {
  const { error } = locationValidation(req.body);
  if (error) {
    return res.status("400").json({ error: error.details[0].message });
  } else {
    // check if user exists
    let existing = await Location.findOne({
      roomName: req.body.roomName,
      level: req.body.level,
    });

    if (existing) {
      return res
        .status("400")
        .json({ error: "Location already exists in database" });
    }
    try {
      const newLocation = new Location({
        roomName: req.body.roomName,
        level: req.body.level,
      });
      const result = await newLocation.save();
      return res.status("200").json({
        message: "New location added",
        saved: result,
      });
    } catch (error) {
      console.log("!!" + error);
      return res.status("500").json({ error: "Failed to add new Location" });
    }
  }
});

router.post("/removeLocation/", async (req, res) => {
  const { error } = locationValidation(req.body);
  if (error) {
    return res.status("400").json({ error: error.details[0].message });
  } else {
    // check if user exists
    let existing = await Location.findOne({
      roomName: req.body.roomName,
      level: req.body.level,
    });

    if (!existing) {
      return res.status("400").json({ error: "Location not in the database" });
    }
    try {
      const logsRemoved = await Log.deleteMany({ location: existing._id });
      const result = await Location.deleteOne({
        roomName: req.body.roomName,
        level: req.body.level,
      });

      return res.status("200").json({
        message: "Location deleted",
        deleted: { ...result, logsRemoved },
      });
    } catch (error) {
      console.log("!!" + error);
      return res.status("500").json({ error: "Failed to delete Location" });
    }
  }
});

router.post("/addDevice/", async (req, res) => {
  const { error } = deviceValidation(req.body);
  if (error) {
    return res.status("400").json({ error: error.details[0].message });
  } else {
    // check if user exists
    let existing = await Device.findOne({
      apiKey: req.body.apiKey,
    });

    if (existing) {
      return res.status("400").json({ error: "Cannot have duplicate api key" });
    }
    try {
      const newDevice = new Device({
        name: req.body.name,
        apiKey: req.body.apiKey,
      });
      const result = await newDevice.save();
      return res.status("200").json({
        message: "New device added",
        saved: result,
      });
    } catch (error) {
      console.log("!!" + error);
      return res.status("500").json({ error: "Failed to add new Device" });
    }
  }
});

router.post("/removeDevice/", async (req, res) => {
  const { error } = deviceValidation(req.body);
  if (error) {
    return res.status("400").json({ error: error.details[0].message });
  } else {
    // check if user exists
    let existing = await Device.findOne({
      name: req.body.name,
      apiKey: req.body.apiKey,
    });

    if (!existing) {
      return res.status("400").json({ error: "Device does not exist" });
    }
    try {
      const result = await Device.deleteOne({
        name: req.body.name,
        apiKey: req.body.apiKey,
      });
      return res.status("200").json({
        message: "Device deleted",
        deleted: result,
      });
    } catch (error) {
      console.log("!!" + error);
      return res.status("500").json({ error: "Failed to delete Device" });
    }
  }
});
module.exports = router;

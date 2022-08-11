const { Mongoose } = require("mongoose");
const supertest = require("supertest");
const app = require("./../../src/utils/server")();

describe("Administration Utilities", () => {
  let jwt = "";

  describe("Login", () => {
    it("Admin Credential", async () => {
      const { statusCode, body } = await supertest(app)
        .post("/api/auth/login")
        .send({
          nhsid: "devtesting",
          password: "devtesting",
        });
      expect(statusCode).toBe(200);
      if (statusCode === 200) {
        jwt = body.signedToken;
      }
    });
  });

  describe("Register", () => {
    it("User Registration and Unregister", async () => {
      const { statusCode, body } = await supertest(app)
        .post("/api/admin/register")
        .set("Authorization", `Bearer ${jwt}`)
        .send({
          nhsid: "testinguser",
          password: "testinguser",
          privilege: "user",
        });
      expect(statusCode).toBe(200);

      await supertest(app)
        .post("/api/admin/unregister")
        .set("Authorization", `Bearer ${jwt}`)
        .send({
          nhsid: "testinguser",
          type: "user",
        })
        .expect(200);

      await supertest(app)
        .post("/api/admin/registerPatient")
        .set("Authorization", `Bearer ${jwt}`)
        .send({
          nhsid: "testingPatient",
          firstname: "First",
          lastname: "Last",
        })
        .expect(200);

      await supertest(app)
        .post("/api/admin/unregister")
        .set("Authorization", `Bearer ${jwt}`)
        .send({
          nhsid: "testingPatient",
          type: "patient",
        })
        .expect(200);
    });
  });

  describe("User", () => {
    beforeAll(async () => {
      await supertest(app)
        .post("/api/admin/registerPatient")
        .set("Authorization", `Bearer ${jwt}`)
        .send({
          nhsid: "testingPatient",
          firstname: "First",
          lastname: "Last",
        })
        .expect(200);
    });

    describe("Find all", () => {
      it("No access right", async () => {
        await supertest(app).get("/api/data/user").expect(401);
      });
      it("Fetching data", async () => {
        await supertest(app)
          .get("/api/data/user")
          .set("Authorization", `Bearer ${jwt}`)
          .expect(200);
      });
    });

    describe("GetQRCode", () => {
      describe("given no authorization", () => {
        it("return 401", async () => {
          await supertest(app)
            .get("/api/admin/getQRCode?nhsid=testingPatient")
            .expect(401);
        });
      });
      describe("given no nhsid", () => {
        it("return 400", async () => {
          await supertest(app)
            .get("/api/admin/getQRCode")
            .set("Authorization", `Bearer ${jwt}`)
            .expect(400);
        });
      });
      describe("given all information", () => {
        it("return 400", async () => {
          await supertest(app)
            .get("/api/admin/getQRCode?nhsid=testingPatient")
            .set("Authorization", `Bearer ${jwt}`)
            .expect(200);
        });
      });
    });
    afterAll(async () => {
      await supertest(app)
        .post("/api/admin/unregister")
        .set("Authorization", `Bearer ${jwt}`)
        .send({
          nhsid: "testingPatient",
          type: "patient",
        })
        .expect(200);
    });
  });

  describe("Location", () => {
    it("given no privilege", async () => {
      await supertest(app)
        .post("/api/admin/addLocation")
        .send({
          roomName: "testRoom",
          level: 2,
        })
        .expect(401);
    });
    it("Adding and removing locations", async () => {
      await supertest(app)
        .post("/api/admin/addLocation")
        .set("Authorization", `Bearer ${jwt}`)
        .send({
          roomName: "testRoom",
          level: 2,
        })
        .expect(200);

      await supertest(app)
        .post("/api/admin/removeLocation")
        .set("Authorization", `Bearer ${jwt}`)
        .send({
          roomName: "testRoom",
          level: 2,
        })
        .expect(200);
    });
  });
  describe("Device", () => {
    it("given no privilege", async () => {
      await supertest(app)
        .post("/api/admin/removeDevice")
        .send({
          name: "Room 305 West Scanner",
          apiKey: "88880000",
        })
        .expect(401);
    });
    it("Adding and removing devices", async () => {
      await supertest(app)
        .post("/api/admin/addDevice")
        .set("Authorization", `Bearer ${jwt}`)
        .send({
          name: "Room 305 West Scanner",
          apiKey: "88880000",
        })
        .expect(200);

      await supertest(app)
        .post("/api/admin/removeDevice")
        .set("Authorization", `Bearer ${jwt}`)
        .send({
          name: "Room 305 West Scanner",
          apiKey: "88880000",
        })
        .expect(200);
    });
  });
  afterAll(async () => {});
});

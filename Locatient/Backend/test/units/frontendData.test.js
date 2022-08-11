const supertest = require("supertest");
const app = require("./../../src/utils/server")();

describe("Frontend Utilities", () => {
  let jwt = "";

  describe("Login", () => {
    it("User Credential", async () => {
      const { statusCode, body } = await supertest(app)
        .post("/api/auth/login")
        .send({
          nhsid: "devtestinguser",
          password: "devtestinguser",
        });
      expect(statusCode).toBe(200);
      if (statusCode === 200) {
        jwt = body.signedToken;
      }
    });
  });

  describe("Data Requests", () => {
    describe("devices", () => {
      it("No access right", async () => {
        await supertest(app).get("/api/data/device").expect(401);
      });
      it("Fetching data", async () => {
        await supertest(app)
          .get("/api/data/device")
          .set("Authorization", `Bearer ${jwt}`)
          .expect(200);
      });
    });
    describe("locations", () => {
      it("No access right", async () => {
        await supertest(app).get("/api/data/location").expect(401);
      });
      it("Fetching data", async () => {
        await supertest(app)
          .get("/api/data/location")
          .set("Authorization", `Bearer ${jwt}`)
          .expect(200);
      });
    });
    describe("patients", () => {
      it("No access right", async () => {
        await supertest(app).get("/api/data/patient").expect(401);
      });
      it("Fetching data", async () => {
        await supertest(app)
          .get("/api/data/patient")
          .set("Authorization", `Bearer ${jwt}`)
          .expect(200);
      });
    });
    describe("logs", () => {
      let adminjwt = "";

      describe("Login", () => {
        it("Creating Authentication", async () => {
          const { statusCode, body } = await supertest(app)
            .post("/api/auth/login")
            .send({
              nhsid: "devtesting",
              password: "devtesting",
            });
          expect(statusCode).toBe(200);
          if (statusCode === 200) {
            adminjwt = body.signedToken;
          }
        });
      });

      describe("Default", () => {
        it("No access right", async () => {
          await supertest(app).get("/api/data/log/").expect(401);
        });
        it("Fetching data", async () => {
          await supertest(app)
            .get("/api/data/log/")
            .set("Authorization", `Bearer ${jwt}`)
            .expect(200);
        });
        it("Data limiting", async () => {
          await supertest(app)
            .get("/api/data/log/?limit=5")
            .set("Authorization", `Bearer ${jwt}`)
            .expect(200);
        });
      });
      describe("Filter through patient id", () => {
        beforeAll(async () => {
          await supertest(app)
            .post("/api/admin/registerPatient")
            .set("Authorization", `Bearer ${adminjwt}`)
            .send({
              nhsid: "testingPatient",
              firstname: "First",
              lastname: "Last",
            })
            .expect(200);
        });

        it("No access right", async () => {
          await supertest(app)
            .get("/api/data/log/patient/testingPatient")
            .expect(401);
        });
        it("Fetching data", async () => {
          await supertest(app)
            .get("/api/data/log/patient/testingPatient")
            .set("Authorization", `Bearer ${jwt}`)
            .expect(200);
        });
        it("Validify id", async () => {
          await supertest(app)
            .get("/api/data/log/patient/wrongValue")
            .set("Authorization", `Bearer ${jwt}`)
            .expect(400);
        });
        afterAll(async () => {
          await supertest(app)
            .post("/api/admin/unregister")
            .set("Authorization", `Bearer ${adminjwt}`)
            .send({
              nhsid: "testingPatient",
              type: "patient",
            })
            .expect(200);
        });
      });
      describe("Filter through location", () => {
        beforeAll(async () => {
          await supertest(app)
            .post("/api/admin/addLocation")
            .set("Authorization", `Bearer ${adminjwt}`)
            .send({
              roomName: "testRoom",
              level: 2,
            })
            .expect(200);
        });

        it("No access right", async () => {
          await supertest(app)
            .get("/api/data/log/location/testRoom/2")
            .expect(401);
        });
        it("Fetching data", async () => {
          await supertest(app)
            .get("/api/data/log/location/testRoom/2")
            .set("Authorization", `Bearer ${jwt}`)
            .expect(200);
        });
        it("Validify location", async () => {
          await supertest(app)
            .get("/api/data/log/location/testRoom/3")
            .set("Authorization", `Bearer ${jwt}`)
            .expect(400);
        });
        afterAll(async () => {
          await supertest(app)
            .post("/api/admin/removeLocation")
            .set("Authorization", `Bearer ${adminjwt}`)
            .send({
              roomName: "testRoom",
              level: 2,
            })
            .expect(200);
        });
      });
    });
  });

  afterAll(async () => {});
});

const supertest = require("supertest");
const app = require("./../../src/utils/server")();

describe("Logging Utilities", () => {
  let apiKey = "88880000";
  let jwt = "";
  let hash = "";
  describe("Logging Device", () => {
    it("Constructing Device", async () => {
      const res = await supertest(app).post("/api/auth/login").send({
        nhsid: "devtesting",
        password: "devtesting",
      });
      if (res.statusCode == 200) {
        jwt = res.body.signedToken;
      }

      await supertest(app)
        .post("/api/admin/addDevice")
        .set("Authorization", `Bearer ${jwt}`)
        .send({
          name: "TestDevice",
          apiKey: "88880000",
        })
        .expect(200);

      await supertest(app)
        .post("/api/admin/addLocation")
        .set("Authorization", `Bearer ${jwt}`)
        .send({
          roomName: "testRoom",
          level: 2,
        })
        .expect(200);

      const { statusCode, body } = await supertest(app)
        .post("/api/admin/registerPatient")
        .set("Authorization", `Bearer ${jwt}`)
        .send({
          nhsid: "testingPatient",
          firstname: "First",
          lastname: "Last",
        });
      expect(statusCode).toBe(200);
      hash = body.saved.qrhash;
    });
  });

  describe("Find preset locations", () => {
    it("No access right", async () => {
      await supertest(app).get("/api/log/locationList").expect(401);
    });
    it("Fetching data", async () => {
      await supertest(app)
        .get("/api/log/locationList")
        .set("Authorization", `${apiKey}`)
        .expect(200);
    });
  });

  describe("Updating new logs", () => {
    it("No access right", async () => {
      await supertest(app).post("/api/log/update").expect(401);
    });
    it("Updating data", async () => {
      await supertest(app)
        .post("/api/log/update")
        .set("Authorization", `${apiKey}`)
        .send({
          qrhash: hash,
          roomName: "testRoom",
          level: 2,
          date: new Date().toISOString(),
        })
        .expect(200);
    });
  });

  afterAll(async () => {
    await supertest(app)
      .post("/api/admin/removeDevice")
      .set("Authorization", `Bearer ${jwt}`)
      .send({
        name: "TestDevice",
        apiKey: "88880000",
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

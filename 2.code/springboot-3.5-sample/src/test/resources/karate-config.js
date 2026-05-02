function fn() {
  var serverPort = karate.properties["serverPort"] || "9090";
  var config = {
    baseUrl: karate.properties["baseUrl"] || "http://localhost:" + serverPort,
  };
  return config;
}

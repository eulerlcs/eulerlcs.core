Feature: User API e2e template

  Background:
    * url baseUrl

  Scenario: hello endpoint should be available
    Given path 'api', 'hello'
    When method get
    Then status 200
    And match response == 'Hello, World!'

  Scenario: user CRUD flow should work end-to-end
    * def suffix = karate.uuid()
    * def username = 'e2e_' + suffix
    * def payload =
      """
      {
        "username": "#(username)",
        "password": "e2e-password",
        "email": "e2e+#(suffix)@example.com"
      }
      """

    Given path 'api', 'users'
    And request payload
    When method post
    Then status 201
    And match response.id == '#number'
    And match response.username == username
    And match response.email == 'e2e+' + suffix + '@example.com'
    * def userId = response.id

    Given path 'api', 'users', userId
    When method get
    Then status 200
    And match response.id == userId
    And match response.username == username

    * def updatedPayload =
      """
      {
        "username": "#(username + '_updated')",
        "password": "e2e-password-updated",
        "email": "e2e.updated+#(suffix)@example.com"
      }
      """

    Given path 'api', 'users', userId
    And request updatedPayload
    When method put
    Then status 200
    And match response.id == userId
    And match response.username == username + '_updated'
    And match response.email == 'e2e.updated+' + suffix + '@example.com'

    Given path 'api', 'users', userId
    When method delete
    Then status 204

    Given path 'api', 'users', userId
    When method get
    Then status 404

import React, { useState } from "react";
import { Button, Form } from "react-bootstrap";
import { useContext } from "react";
import { AuthContext } from "../AuthContext";
import "../css/RegisterForm.css";

const RegisterForm = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const { login } = useContext(AuthContext);

  const handleUsername = (event) => {
    setUsername(event.target.value);
  };

  const handleEmail = (event) => {
    setEmail(event.target.value);
  };

  const handlePassword = (event) => {
    setPassword(event.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    let response = await fetch("http://localhost:8080/api/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username,
        email,
        password,
      }),
    });

    if (response.ok) {
      setError(null);
      setSuccessMessage("User registered successfully! Logging in...");

      response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username,
          password,
        }),
      });

      if (response.ok) {
        const data = await response.json();
        login({
          token: data.accessToken,
          username: data.username,
          roles: data.roles,
        });

        setTimeout(() => {
          window.location.replace("/");
        }, 2500);
      } else {
        const errorMessage = await response.text();
        setError(errorMessage);
      }
    } else {
      const errorMessage = await response.text();
      setError(errorMessage);
    }
  };

  return (
    <div className="d-flex flex-column align-items-center justify-content-center login-form-background">
      <img
        src={process.env.PUBLIC_URL + "/logo-transparent-upscaled.png"}
        className="login-form-logo"
        alt="epicode Logo"
      ></img>
      <p className="text-light">Register on THE_GAME_RENDER</p>

      <Form onSubmit={handleSubmit} className="mt-5">
        <Form.Group
          className="mb-3 register-form-field"
          controlId="username"
          onChange={handleUsername}
        >
          <Form.Control required type="text" placeholder="insert username.." />
        </Form.Group>
        <Form.Group
          className="mb-3 register-form-field"
          controlId="email"
          onChange={handleEmail}
        >
          <Form.Control required type="email" placeholder="insert email.." />
        </Form.Group>
        <Form.Group
          className="mb-3 register-form-field"
          controlId="password"
          onChange={handlePassword}
        >
          <Form.Control
            required
            type="password"
            placeholder="insert password.."
          />
        </Form.Group>
        <br />
        <br />
        {error && <p className="register-form-error">{error}</p>}
        {successMessage && (
          <p className="register-form-success">{successMessage}</p>
        )}
        <Button
          type="submit"
          className="btn btn-dark btn-outline-warning my-2 register-form-button"
        >
          Register
        </Button>

        <Button
          type="button"
          className="btn btn-dark btn-outline-success my-2 register-form-button"
          onClick={() => {
            window.location.replace("/login");
          }}
        >
          Already registered? Login!
        </Button>
      </Form>
    </div>
  );
};

export default RegisterForm;

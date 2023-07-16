import React, { useContext, useState } from "react";
import { Button, Form } from "react-bootstrap";
import { AuthContext } from "../AuthContext";
import "../css/LoginForm.css";

const LoginForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handlePassword = (event) => {
    setPassword(event.target.value);
  };

  const handleUsername = (event) => {
    setUsername(event.target.value);
  };

  const { login } = useContext(AuthContext);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const response = await fetch("http://localhost:8080/api/auth/login", {
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
      setError(null);

      console.log(data);

      login({
        token: data.accessToken,
        username: data.username,
        roles: data.roles,
      });
      setTimeout(() => {
        window.location.replace("/");
      }, 3000);
    } else {
      console.log("Server error:", response.statusText);
      setError("Username or password are not correct");
    }
  };

  return (
    <div className="d-flex flex-column align-items-center justify-content-center login-form-background">
      <img
        src={process.env.PUBLIC_URL + "/logo-transparent-upscaled.png"}
        className="login-form-logo"
        alt="epicode Logo"
      ></img>
      <p className="text-light">
        Welcome to <span>THE_GAME_RENDER</span>
      </p>

      <Form onSubmit={handleSubmit} className="mt-5">
        <Form.Group
          className="mb-3 login-form-field"
          controlId="username"
          onChange={handleUsername}
        >
          <Form.Control required type="text" placeholder="insert username.." />
        </Form.Group>
        <Form.Group
          className="mb-3 login-form-field"
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
        {error && <p className="login-form-error">{error}</p>}
        <Button
          type="submit"
          className="btn btn-dark btn-outline-success my-2 login-form-button"
        >
          Login
        </Button>
        <br />
        <Button
          type="button"
          className="btn btn-dark btn-outline-warning my-2 login-form-button"
          onClick={() => {
            window.location.replace("/register");
          }}
        >
          Register now!
        </Button>
      </Form>
    </div>
  );
};

export default LoginForm;

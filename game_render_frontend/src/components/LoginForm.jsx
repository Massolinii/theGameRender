import React, { useContext, useState } from "react";
import { Button, Form } from "react-bootstrap";
import { AuthContext } from "../AuthContext";

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
      /*setTimeout(() => {
        window.location.replace("/");
      }, 5000);*/
    } else {
      console.log("Server error:", response.statusText);
      setError("Username or password are not correct");
    }
  };

  return (
    <div
      className="py-5 bg-dark"
      style={{ display: "block", height: "100vh", vhposition: "initial" }}
    >
      <img
        src="https://epicode.com/wp-content/uploads/2022/06/EPICODE-2.0-LOGO-15.png"
        style={{ width: "30%" }}
        alt="epicode Logo"
      ></img>
      <p className="text-light">Welcome on Epic Energy ⚡️</p>

      <Form onSubmit={handleSubmit} className="mt-5">
        <Form.Group
          className="mb-3 w-25 mx-auto"
          controlId="username"
          onChange={handleUsername}
        >
          <Form.Control required type="text" placeholder="insert username.." />
        </Form.Group>
        <Form.Group
          className="mb-3 w-25 mx-auto"
          controlId="password"
          onChange={handlePassword}
        >
          <Form.Control
            required
            type="password"
            placeholder="insert password.."
          />
        </Form.Group>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <Button
          type="submit"
          className="btn btn-dark btn-outline-warning my-2 w-25 "
        >
          Login
        </Button>
      </Form>

      <button
        type="button"
        className="btn btn-outline-success my-2 w-25 "
        onClick={() => {
          window.location.replace("/register");
        }}
      >
        Register now!
      </button>
    </div>
  );
};

export default LoginForm;

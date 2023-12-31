import React, { createContext, useEffect, useState } from "react";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState({ loading: true });

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const username = localStorage.getItem("username");
      const roles = JSON.parse(localStorage.getItem("roles"));
      setUser({
        token,
        username,
        roles,
        loading: false,
      });
    }
  }, []);

  const login = (userData) => {
    console.log(userData);
    localStorage.setItem("token", userData.token);
    localStorage.setItem("username", userData.username);
    localStorage.setItem("roles", JSON.stringify(Array.from(userData.roles)));
    const roles = new Set(JSON.parse(localStorage.getItem("roles")));
    setUser({
      ...userData,
      loading: false,
    });
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    localStorage.removeItem("roles");
    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

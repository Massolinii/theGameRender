import React, { createContext, useEffect, useState } from "react";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState({ loading: true });

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const username = localStorage.getItem("username");
      setUser({
        token,
        username,
        loading: false,
      });
    }
  }, []);

  const login = (userData) => {
    console.log(userData);
    localStorage.setItem("token", userData.token);
    localStorage.setItem("username", userData.username);
    setUser({
      token: userData.token,
      username: userData.username,
      loading: false,
    });
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
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

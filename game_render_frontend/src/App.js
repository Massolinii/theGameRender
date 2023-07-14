import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HomePage from "./components/HomePage";
import CategoryPage from "./components/CategoryPage";
import "bootstrap/dist/css/bootstrap.min.css";
import HomeNavBar from "./components/HomeNavBar";
import LoginForm from "./components/LoginForm";
import { AuthProvider } from "./AuthContext";

function App() {
  return (
    <AuthProvider>
      <div className="App">
        <HomeNavBar />
        <Router>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<LoginForm />} />
            <Route path="/category/:id" element={<CategoryPage />} />
          </Routes>
        </Router>
      </div>
    </AuthProvider>
  );
}

export default App;

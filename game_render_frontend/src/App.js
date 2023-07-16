import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HomePage from "./components/HomePage";
import CategoryPage from "./components/CategoryPage";
import "bootstrap/dist/css/bootstrap.min.css";
import HomeNavBar from "./components/HomeNavBar";
import LoginForm from "./components/LoginForm";
import { AuthProvider } from "./AuthContext";
import RegisterForm from "./components/RegisterForm";
import CollectionPage from "./components/CollectionPage";

function App() {
  return (
    <AuthProvider>
      <div className="App">
        <Router>
          <HomeNavBar />
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<LoginForm />} />
            <Route path="/register" element={<RegisterForm />} />
            <Route path="/category/:id" element={<CategoryPage />} />
            <Route path="/collection/:id" element={<CollectionPage />} />
          </Routes>
        </Router>
      </div>
    </AuthProvider>
  );
}

export default App;

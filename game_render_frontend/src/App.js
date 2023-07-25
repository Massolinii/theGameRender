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
import Footer from "./components/Footer";
import FavoritesPage from "./components/FavoritesPage";
import HowToWrite from "./components/HowToWrite";
import ScrollToTop from "./components/ScrollToTop";

function App() {
  return (
    <AuthProvider>
      <div className="App">
        <tt>
          <Router>
            <ScrollToTop />
            <HomeNavBar />
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/login" element={<LoginForm />} />
              <Route path="/register" element={<RegisterForm />} />
              <Route path="/category/:id" element={<CategoryPage />} />
              <Route path="/collection/:id" element={<CollectionPage />} />
              <Route path="/favorites" element={<FavoritesPage />} />
              <Route path="/how-to-write-a-prompt" element={<HowToWrite />} />
            </Routes>
            <Footer />
          </Router>
        </tt>
      </div>
    </AuthProvider>
  );
}

export default App;

import React from "react";
import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomePage from "./components/HomePage";
import Category from "./components/Category";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/home" element={<HomePage />} />
          <Route path="/category/:id" element={<Category />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;

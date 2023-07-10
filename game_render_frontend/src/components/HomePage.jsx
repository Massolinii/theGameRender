import React from "react";
import Hero from "./Hero";

function HomePage() {
  return (
    <div>
      <h2>Futura Navbar</h2>
      <Hero />
      <h2>Categorie:</h2>
      <a href="/category/1">Clicca qui per la Categoria 1</a>
    </div>
  );
}

export default HomePage;

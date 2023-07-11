import React, { useState } from "react";
import Hero from "./Hero";
import { Container } from "react-bootstrap";
import HomeNavBar from "./HomeNavBar";

function HomePage() {
  const [showHero] = useState(true); // Show the Hero component immediately

  return (
    <div>
      <Container fluid className="p-0">
        <HomeNavBar show={showHero} />
        <Hero show={showHero} />
        <h2>Categorie:</h2>
        <a href="/category/1">Clicca qui per la Categoria 1</a>
      </Container>
    </div>
  );
}

export default HomePage;

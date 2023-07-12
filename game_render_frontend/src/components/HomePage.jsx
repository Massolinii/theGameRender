import React, { useState } from "react";
import Hero from "./Hero";
import { Container } from "react-bootstrap";
import HomeNavBar from "./HomeNavBar";
import Quest from "./Quest";
import { Link } from "react-router-dom";

function HomePage() {
  const [showHero] = useState(true); // Show the Hero component immediately

  return (
    <div>
      <Container fluid className="p-0">
        <HomeNavBar show={showHero} />
        <Hero show={showHero} />
        <Quest />
        <h2>Categorie:</h2>
        <Link href="/category/1">Clicca qui per la Categoria 1</Link>
      </Container>
    </div>
  );
}

export default HomePage;

import React, { useState } from "react";

import { Container } from "react-bootstrap";
import { Link } from "react-router-dom";
import Hero from "./Hero";
import Quest from "./Quest";
import HomeCategories from "./HomeCategories";

function HomePage() {
  const [showHero] = useState(true); // Show the Hero component immediately

  return (
    <div>
      <Container fluid className="p-0">
        <Hero show={showHero} />
        <Quest />
        <HomeCategories />
        <h2>Categorie:</h2>
        <Link href="/category/1">Clicca qui per la Categoria 1</Link>
      </Container>
    </div>
  );
}

export default HomePage;

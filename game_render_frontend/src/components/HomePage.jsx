import React, { useState, useEffect } from "react";
import { Container } from "react-bootstrap";
import Hero from "./Hero";
import Quest from "./Quest";
import HomeCategories from "./HomeCategories";
import HomeNavBar from "./HomeNavBar";

function HomePage() {
  const [showHero, setShowHero] = useState(false); // Show the Hero component immediately
  const [showNavBar, setShowNavBar] = useState(false);

  useEffect(() => {
    setShowHero(true);
    setShowNavBar(true);
    const navBarTimeout = setTimeout(() => {}, 3000);

    return () => {
      clearTimeout(navBarTimeout);
    };
  }, []);

  return (
    <div>
      <Container fluid className="p-0">
        <Hero show={showHero} />
        <HomeNavBar show={showNavBar} />
        <Quest />
        <HomeCategories />
      </Container>
    </div>
  );
}

export default HomePage;

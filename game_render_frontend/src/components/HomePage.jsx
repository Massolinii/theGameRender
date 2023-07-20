import React, { useState, useEffect } from "react";
import { Container } from "react-bootstrap";
import Hero from "./Hero";
import Quest from "./Quest";
import HomeCategories from "./HomeCategories";
import HomeNavBar from "./HomeNavBar";
import LearnToWriteHome from "./LearnToWriteHome";
import Search from "./Search";

function HomePage() {
  const [showHero, setShowHero] = useState(false);
  const [showNavBar, setShowNavBar] = useState(false);

  useEffect(() => {
    setShowHero(true);
    setShowNavBar(true);
  });

  return (
    <div>
      <Container fluid className="p-0">
        <Hero show={showHero} />
        <HomeNavBar show={showNavBar} />
        <Quest />
        <HomeCategories />
        <Search />
        <LearnToWriteHome />
      </Container>
    </div>
  );
}

export default HomePage;

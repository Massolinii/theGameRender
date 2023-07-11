import React, { useEffect, useRef, useState } from "react";
import "../css/Hero.css";
import { Row } from "react-bootstrap";

function Hero({ show }) {
  const letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  const h1Ref = useRef();
  const logoRef = useRef();
  const [logoAnimationCompleted, setLogoAnimationCompleted] = useState(false);
  const [showTitle, setShowTitle] = useState(false);

  // Effect for animating the title
  useEffect(() => {
    let interval = null;

    if (h1Ref.current && show && logoAnimationCompleted && showTitle) {
      const value = h1Ref.current.dataset.value;
      h1Ref.current.innerText = value;

      let iteration = 0;

      interval = setInterval(() => {
        h1Ref.current.innerText = value
          .split("")
          .map((letter, index) =>
            index < iteration
              ? value[index]
              : letters[Math.floor(Math.random() * 26)]
          )
          .join("");

        if (iteration >= value.length) {
          clearInterval(interval);
        }

        iteration += 1 / 3;
      }, 55); // Delay between each iteration of the text animations in ms
    }

    return () => {
      clearInterval(interval);
    };
  }, [show, logoAnimationCompleted, showTitle]);

  // Effect for animating the logo
  useEffect(() => {
    if (show) {
      logoRef.current.style.transform = "translateX(0)";
      setTimeout(() => {
        setLogoAnimationCompleted(true);
      }, 1100); // Duration of the logo animation in milliseconds
    }
  }, [show]);

  // Effect for showing the title after the logo animation
  useEffect(() => {
    if (logoAnimationCompleted) {
      setTimeout(() => {
        setShowTitle(true);
      }, 100); // Delay to allow the logo to stabilize itself before the title appears
    }
  }, [logoAnimationCompleted]);

  return (
    <div className={`hero-section ${show ? "show" : ""}`} id="tiles">
      <div className="hero-content">
        <Row className="hero-rows">
          <img
            src={process.env.PUBLIC_URL + "/logo-transparent-upscaled.png"}
            alt="Logo"
            className={`heroLogo ${show ? "show" : ""}`}
            ref={logoRef}
          />
        </Row>
        <Row className="hero-rows">
          {showTitle && (
            <div className="heroTitleContainer">
              <h1
                ref={h1Ref}
                data-value="THE_GAME_RENDER"
                id="thegamerender"
                className={`heroTitle ${show ? "show" : ""}`}
              >
                THE_GAME RENDER
              </h1>
            </div>
          )}
        </Row>
      </div>
    </div>
  );
}

export default Hero;

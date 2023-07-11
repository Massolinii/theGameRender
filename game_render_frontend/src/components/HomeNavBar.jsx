import React, { useEffect, useState } from "react";
import { Navbar, Nav, NavItem } from "react-bootstrap";
import classnames from "classnames";
import "../css/HomeNavBar.css";

function HomeNavBar() {
  const [showTitle, setShowTitle] = useState(false);
  const [showLinks, setShowLinks] = useState(false);

  useEffect(() => {
    const titleTimeout = setTimeout(() => {
      setShowTitle(true);
    }, 3000);

    const linksTimeout = setTimeout(() => {
      setShowLinks(true);
    }, 3000);

    return () => {
      clearTimeout(titleTimeout);
      clearTimeout(linksTimeout);
    };
  }, []);

  const titleClasses = classnames("navbar-brand", "hidden", {
    "title-animation": showTitle,
  });

  const linkClasses = (index) =>
    classnames("hidden", { [`link-animation-${index}`]: showLinks });

  const separatorClasses = classnames("hidden", {
    "separator-animation": showLinks,
  });

  return (
    <tt>
      <Navbar bg="dark" variant="dark" sticky="top" className="px-3">
        <Navbar.Brand href="/" className={titleClasses}>
          the game render
        </Navbar.Brand>
        <Navbar.Brand href="/" className={separatorClasses}>
          ||
        </Navbar.Brand>
        <Nav className="mr-auto">
          <Nav.Link href="/" className={linkClasses(1)}>
            Home
          </Nav.Link>
          <Nav.Link className={separatorClasses}>|</Nav.Link>
          <Nav.Link href="/category/1" className={linkClasses(2)}>
            Environments
          </Nav.Link>
          <Nav.Link className={separatorClasses}>|</Nav.Link>
          <Nav.Link href="/category/1" className={linkClasses(3)}>
            Objects and Tools
          </Nav.Link>
          <Nav.Link className={separatorClasses}>|</Nav.Link>
          <Nav.Link href="/category/1" className={linkClasses(4)}>
            Characters
          </Nav.Link>
        </Nav>
      </Navbar>
    </tt>
  );
}

export default HomeNavBar;

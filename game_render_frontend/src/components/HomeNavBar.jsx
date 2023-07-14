import React, { useContext, useEffect, useState } from "react";
import { Navbar, Nav, NavDropdown } from "react-bootstrap";
import classnames from "classnames";
import "../css/HomeNavBar.css";
import { AuthContext } from "../AuthContext";

function HomeNavBar() {
  const { user, logout } = useContext(AuthContext);
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
      <Navbar bg="dark" variant="dark" fixed="top" expand="lg" className="px-3">
        <Navbar.Brand href="/" className={titleClasses}>
          the game render
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Navbar.Brand href="/" className={separatorClasses}>
            ||
          </Navbar.Brand>

          <Nav className="mr-auto">
            <Nav.Link
              href="/"
              className={classnames(linkClasses(1), "navbar-toggle-padding")}
            >
              Home
            </Nav.Link>
            <Nav.Link className={separatorClasses}>|</Nav.Link>
            <Nav.Link
              href="/category/1"
              className={classnames(linkClasses(2), "navbar-toggle-padding")}
            >
              Environments
            </Nav.Link>
            <Nav.Link className={separatorClasses}>|</Nav.Link>
            <Nav.Link
              href="/category/2"
              className={classnames(linkClasses(3), "navbar-toggle-padding")}
            >
              Objects and Tools
            </Nav.Link>
            <Nav.Link className={separatorClasses}>|</Nav.Link>
            <Nav.Link
              href="/category/3"
              className={classnames(linkClasses(4), "navbar-toggle-padding")}
            >
              Characters
            </Nav.Link>
          </Nav>
          <Nav className="ml-auto">
            {user && !user.loading ? (
              <>
                <Nav.Link
                  className={classnames(
                    linkClasses(5),
                    "navbar-toggle-padding"
                  )}
                >
                  Welcome, {user.username}.
                </Nav.Link>
                <Nav.Link
                  onClick={logout}
                  className={classnames(
                    linkClasses(6),
                    "navbar-toggle-padding"
                  )}
                >
                  Logout
                </Nav.Link>
              </>
            ) : (
              <Nav.Link
                href="/login"
                className={classnames(linkClasses(6), "navbar-toggle-padding")}
              >
                Login
              </Nav.Link>
            )}
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    </tt>
  );
}

export default HomeNavBar;

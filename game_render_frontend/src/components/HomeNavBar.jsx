import React, { useContext, useEffect, useState } from "react";
import { Navbar, Nav, NavDropdown } from "react-bootstrap";
import classnames from "classnames";
import "../css/HomeNavBar.css";
import { AuthContext } from "../AuthContext";
import { useLocation } from "react-router-dom";

function HomeNavBar({ show }) {
  const { user, logout } = useContext(AuthContext);

  const location = useLocation();
  const isHomePage = location.pathname === "/";

  const [isMdScreen, setIsMdScreen] = useState(window.innerWidth >= 768);
  const [isLgScreen, setIsLgScreen] = useState(window.innerWidth >= 1200);

  useEffect(() => {
    const handleResize = () => {
      setIsLgScreen(window.innerWidth >= 1200);
      setIsMdScreen(window.innerWidth >= 768);
    };

    window.addEventListener("resize", handleResize);
    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  const titleClasses = classnames("navbar-brand", "hidden", {
    "title-animation": isHomePage,
    visible: !isHomePage,
  });

  const linkClasses = (index) =>
    classnames("hidden", {
      [`link-animation-${index}`]: isHomePage,
      visible: !isHomePage,
    });

  const separatorClasses = classnames("hidden", {
    "separator-animation": isHomePage,
    visible: !isHomePage,
  });

  return (
    <tt>
      <Navbar bg="dark" variant="dark" fixed="top" expand="md" className="px-3">
        <Navbar.Brand href="/" className={titleClasses}>
          the game render
        </Navbar.Brand>
        <Navbar.Toggle />
        <Navbar.Collapse>
          {isMdScreen ? (
            <>
              <Navbar.Brand href="/" className={separatorClasses}>
                ||
              </Navbar.Brand>
            </>
          ) : (
            <></>
          )}

          <Nav className="mr-auto">
            <Nav.Link href="/" className={linkClasses(1)}>
              Home
            </Nav.Link>
            {isMdScreen ? (
              <>
                <Nav.Link className={separatorClasses}>|</Nav.Link>
              </>
            ) : (
              <></>
            )}

            {/* Categories Dropdown */}
            {isLgScreen && (
              <>
                <Nav.Link href="/category/1" className={linkClasses(2)}>
                  Environments
                </Nav.Link>
                <Nav.Link className={separatorClasses}>|</Nav.Link>
                <Nav.Link href="/category/2" className={linkClasses(3)}>
                  Objects and Tools
                </Nav.Link>
                <Nav.Link className={separatorClasses}>|</Nav.Link>
                <Nav.Link href="/category/3" className={linkClasses(4)}>
                  Characters
                </Nav.Link>
              </>
            )}

            {!isLgScreen && (
              <NavDropdown
                title="Categories"
                className={classnames(linkClasses(2), "dark-dropdown")}
              >
                <NavDropdown.Item href="/category/1">
                  Environments
                </NavDropdown.Item>
                <NavDropdown.Item href="/category/2">
                  Objects and Tools
                </NavDropdown.Item>
                <NavDropdown.Item href="/category/3">
                  Characters
                </NavDropdown.Item>
              </NavDropdown>
            )}
          </Nav>

          <Nav className="ms-auto">
            {user && !user.loading ? (
              <>
                {isMdScreen ? (
                  <>
                    <Nav.Link className={linkClasses(5)}>
                      {user.username}
                    </Nav.Link>
                    <Nav.Link className={separatorClasses}>|</Nav.Link>
                    <Nav.Link className={linkClasses(6)} href="/favorites">
                      Favorites
                    </Nav.Link>
                    <Nav.Link className={separatorClasses}>|</Nav.Link>
                    <Nav.Link onClick={logout} className={linkClasses(6)}>
                      Logout
                    </Nav.Link>
                  </>
                ) : (
                  <>
                    <Nav.Link className={linkClasses(5)}>
                      {user.username}
                    </Nav.Link>
                    <Nav.Link className={linkClasses(6)} href="/favorites">
                      Favorites
                    </Nav.Link>
                    <Nav.Link onClick={logout} className={linkClasses(6)}>
                      Logout
                    </Nav.Link>
                  </>
                )}
              </>
            ) : (
              <>
                {isMdScreen ? (
                  <>
                    <Nav.Link href="/login" className={linkClasses(6)}>
                      Login
                    </Nav.Link>
                    <Nav.Link className={separatorClasses}>|</Nav.Link>
                    <Nav.Link href="/register" className={linkClasses(6)}>
                      Register
                    </Nav.Link>
                  </>
                ) : (
                  <>
                    <Nav.Link href="/login" className={linkClasses(6)}>
                      Login
                    </Nav.Link>
                    <Nav.Link href="/register" className={linkClasses(6)}>
                      Register
                    </Nav.Link>
                  </>
                )}
              </>
            )}
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    </tt>
  );
}

export default HomeNavBar;

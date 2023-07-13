import { Link } from "react-router-dom";
import "../css/HomeCategories.css";
import { Col, Container, Row } from "react-bootstrap";

function HomeCategories() {
  return (
    <Container fluid className="position-relative">
      <Row className="categories-bg env-category-bg">
        <Col className="d-flex align-items-center justify-content-start">
          <Link to={"/category/1"} className="home-categories-link">
            <h2 className="category-name">Environment</h2>
          </Link>
        </Col>
      </Row>

      <Row className="categories-bg  obj-category-bg">
        <Col className="d-flex align-items-center justify-content-start">
          <Link to={"/category/2"} className="home-categories-link">
            <h2 className="category-name">Objects & Tools</h2>
          </Link>
        </Col>
      </Row>

      <Row className="categories-bg char-category-bg">
        <Col className="d-flex align-items-center justify-content-start">
          <Link to={"/category/3"} className="home-categories-link">
            <h2 className="category-name">Characters</h2>
          </Link>
        </Col>
      </Row>
    </Container>
  );
}

export default HomeCategories;

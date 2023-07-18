import { Link } from "react-router-dom";
import "../css/HomeCategories.css";
import { Col, Container, Row } from "react-bootstrap";

function HomeCategories() {
  const handleMouseEnter = (e) => {
    e.currentTarget.querySelector('.background-image').style.filter = 'brightness(115%)';
  }

  const handleMouseLeave = (e) => {
    e.currentTarget.querySelector('.background-image').style.filter = 'brightness(90%)';
  }

  return (
    <Container fluid className="position-relative">
      <Row className="categories-bg"
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}>
        <div className="background-image env-category-bg"></div>
        <Col className="d-flex align-items-center justify-content-start">
          <Link to={"/category/1"} className="home-categories-link">
            <h2 className="category-name">Environment</h2>
          </Link>
        </Col>
      </Row>

      <Row className="categories-bg"
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}>
        <div className="background-image obj-category-bg"></div>
        <Col className="d-flex align-items-center justify-content-start">
          <Link to={"/category/2"} className="home-categories-link">
            <h2 className="category-name">Objects & Tools</h2>
          </Link>
        </Col>
      </Row>

      <Row className="categories-bg"
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}>
        <div className="background-image char-category-bg"></div>
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

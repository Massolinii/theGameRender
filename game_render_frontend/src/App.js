import logo from './logo.svg';
import './App.css';
import HomePage from './components/HomePage';
import { Container } from "react-bootstrap";

function App() {
  return (
    <div className="App">
      <Container>
        <HomePage />
      </Container>
    </div>
  );
}

export default App;

import React, { useEffect, useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import { updateImage, deleteImage } from "../api.js";

import "../css/Modals.css";

const ImageEditModal = ({
  isOpen,
  onClose,
  image,
  onUpdateSuccess,
  handleImageUpdate,
}) => {
  const [promptText, setPromptText] = useState(image.promptText);
  const [error, setError] = useState("");

  useEffect(() => {
    if (image) {
      setPromptText(image.promptText);
    }
  }, [image]);

  const handleUpdate = async (e) => {
    e.preventDefault();

    if (!promptText) {
      setError("Prompt Text is required.");
      return;
    }

    const imageData = {
      promptText: promptText,
    };

    try {
      await handleImageUpdate(image.imageID, { promptText });
      onUpdateSuccess("The image was updated successfully");
      onClose();
    } catch (error) {
      setError("An error occurred during updating" + error);
    }
  };

  const handleImageDelete = async () => {
    if (window.confirm("Are you sure you want to delete this image?")) {
      try {
        await deleteImage(image.imageID);
        console.log("The image was deleted successfully");
        onClose();
      } catch (error) {
        setError("An error occurred during deleting" + error);
      }
    }
  };

  return (
    <Modal show={isOpen} onHide={onClose} className="dark-modal">
      <Modal.Header closeButton>
        <Modal.Title>Edit Image</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleUpdate}>
          <Form.Group>
            <Form.Label>Modify Prompt Text</Form.Label>
            <Form.Control
              as="textarea"
              rows={4}
              placeholder="Prompt Text"
              className="my-2"
              value={promptText}
              onChange={(e) => setPromptText(e.target.value)}
            />
          </Form.Group>
          {error && <div className="text-danger mb-2">{error}</div>}
          <Modal.Footer>
            <Button variant="primary" type="submit">
              Update
            </Button>
            <Button variant="danger" onClick={handleImageDelete}>
              Delete
            </Button>
          </Modal.Footer>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default ImageEditModal;

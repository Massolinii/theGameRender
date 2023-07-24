import React, { useState, useEffect } from "react";
import { Button, Modal, Form, Alert } from "react-bootstrap";
import { updateCollection } from "../api.js";

const CollectionEditModal = ({
  isOpen,
  onClose,
  collection,
  onUpdateSuccess,
}) => {
  const [collectionName, setCollectionName] = useState(
    collection ? collection.collectionName : ""
  );
  const [error, setError] = useState("");

  useEffect(() => {
    if (collection) {
      setCollectionName(collection.collectionName);
    }
  }, [collection]);

  const handleUpdate = async (e) => {
    e.preventDefault();

    if (!collectionName) {
      setError("Collection Name is required.");
      return;
    }

    const response = await updateCollection(collection.collectionID, {
      name: collectionName,
    });

    if (response.ok) {
      onClose();
      onUpdateSuccess("The collection was updated successfully");
    } else {
      const data = await response.json();
      setError(data.message || "An error occurred during updating");
    }
  };

  return (
    <Modal show={isOpen} onHide={onClose}>
      <Modal.Header closeButton>
        <Modal.Title>Edit Collection</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleUpdate}>
          <Form.Group>
            <Form.Label>Collection Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Collection Name"
              value={collectionName}
              onChange={(e) => setCollectionName(e.target.value)}
            />
          </Form.Group>
          {error && <Alert variant="danger">{error}</Alert>}
          <Button type="submit">Update</Button>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onClose}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default CollectionEditModal;

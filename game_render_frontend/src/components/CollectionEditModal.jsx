import React, { useState, useEffect } from "react";
import { Button, Modal, Form, Alert } from "react-bootstrap";
import { updateCollection, deleteCollection } from "../api.js";

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

    const collectionData = {
      collectionName: collectionName,
    };

    try {
      const updatedCollection = await updateCollection(
        collection.collectionID,
        collectionData
      );
      onClose();
      onUpdateSuccess(
        `The collection "${updatedCollection.collectionName}" was updated successfully`
      );
    } catch (error) {
      setError(error.message || "An error occurred during updating");
    }
  };

  const handleDelete = async () => {
    try {
      await deleteCollection(collection.collectionID);
      onClose();
      onUpdateSuccess(
        `The collection "${collection.collectionName}" was deleted successfully`
      );
    } catch (error) {
      setError(error.message || "An error occurred during deletion");
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
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button type="submit" className="my-2">
          Update
        </Button>{" "}
        <Button variant="danger" onClick={handleDelete}>
          Delete
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default CollectionEditModal;

// import node module libraries
import React, { ReactNode, Fragment, useState, useEffect } from 'react';
import Link from 'next/link';
import {
  Row,
  Col,
  Container,
  Form,
  Button,
  Modal,
  Alert
} from 'react-bootstrap';

import useMounted from '../hooks/useMounted';
import transactionService from '../services/transactionService';

const WithdrawModal = (props: any) => {
    const { setShowWithdrawModal, user, selectedAccount, show, onHide } = props;

    const hasMounted = useMounted();

    const [amount, setAmount] = useState("");
    const [error, setError] = useState(null);

    useEffect(() => {
      setAmount("")
      setError(null)
    }, [show]);

    const handleSubmit = () => {
      const payload = {
        accountUuid: selectedAccount,
        amount: amount
      }

      const accessToken = user.getSignInUserSession().getAccessToken().getJwtToken()
      transactionService.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`
      transactionService.post('/v1/transaction/withdraw', payload)
        .then(() => {
          setShowWithdrawModal(false)
        })
        .catch((e) => {
          setError(e.response.data.message)
        })
    }

	return (
        <Fragment>
            <Modal
              show={show}
              onHide={onHide}
              aria-labelledby="contained-modal-title-vcenter"
              centered
            >
              <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    <span>Withdraw</span>
                </Modal.Title>
              </Modal.Header>
              <Modal.Body>
                {error && (
                  <Alert variant="danger" onClose={() => setError(null)} dismissible>
                    {error}
                  </Alert>
                )}
                {hasMounted && (
                <Form>
                    <Row>
                      <Col xs={12} className="mb-3">
                        <label htmlFor="amount">Amount</label>
                        <Form.Control
                          type="number"
                          id="amount"
                          value={amount}
                          onChange={e => setAmount(e.target.value)}
                          required
                        />
                      </Col>
                      <Col xs={12} className="mb-0 d-grid gap-2">
                        {/* Button */}
                        <Button className="btn btn-primary" onClick={() => handleSubmit()}>
                          Submit
                        </Button>
                      </Col>
                    </Row>
                </Form>
                )}
              </Modal.Body>
            </Modal>
        </Fragment>
    );
};


export default WithdrawModal;

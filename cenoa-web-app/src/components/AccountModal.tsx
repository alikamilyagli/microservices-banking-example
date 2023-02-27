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
import accountService from '../services/accountService';

const AccountModal = (props: any) => {
    const { setShowAccountModal, user, selectedAccount, show, onHide } = props;

    const hasMounted = useMounted();

    const [currencyEnums, setCurrencyEnums] = useState<any[]>([])
    const [statusEnums, setStatusEnums] = useState<any[]>([])

    const [status, setStatus] = useState("ACTIVE");
    const [currency, setCurrency] = useState("USD");
    const [description, setDescription] = useState("");
    const [error, setError] = useState(null);
    const [enums, setEnums] = useState(null);
    const [accessToken, setAccesstoken] = useState(null);

    useEffect(() => {
      if ("" == selectedAccount) {
          setError(null)
          setDescription("")
          setCurrency("USD")
          setStatus("ACTIVE")
      }
    }, [show, selectedAccount]);

    useEffect(() => {
      const accessToken = user.getSignInUserSession().getAccessToken().getJwtToken()
      setAccesstoken(accessToken)
      accountService.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`
      accountService.get('/v1/enum')
        .then(response => {
          const data = response.data.data
          setCurrencyEnums(data.CURRENCY)
          setStatusEnums(data.STATUS)
        })
        .catch(e => {
          console.error(e)
        })

    }, [user]);

    useEffect(() => {
        if("" != selectedAccount) {
          const accessToken = user.getSignInUserSession().getAccessToken().getJwtToken()
          setAccesstoken(accessToken)
          accountService.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`
          accountService.get('/v1/account/' + selectedAccount)
            .then(response => {
              const data = response.data.data
              setStatus(data.status)
              setCurrency(data.currency)
              setDescription(data.description)
            })
            .catch(e => {
              console.error(e)
            })
        }
    }, [user, selectedAccount]);

    const handleSubmit = () => {
      const payload = {
        currency: currency,
        description: description,
        status: status
      }

      accountService.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`
      if ("" == selectedAccount) {
        accountService.post('/v1/account', payload)
          .then(() => {
              setShowAccountModal(false)
          })
          .catch((e) => {
             setError(e.response.data.message)
          })
      }
      else {
        accountService.put('/v1/account/' + selectedAccount, payload)
          .then(() => {
              setShowAccountModal(false)
          })
          .catch((e) => {
             setError(e.response.data.message)
          })
      }
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
                  {"" == selectedAccount ?
                    <span>Add New Account</span>
                    :
                    <span>Update Account</span>
                  }
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
                        <label htmlFor="status">Status</label>
                        <Form.Select
                          aria-label="Status"
                          id="status"
                          value={status}
                          onChange={e => setStatus(e.target.value)}
                        >
                          {statusEnums.length > 0 && statusEnums.map((item, idx) => (
                            <option value={item} key={idx}>{item}</option>
                          ))}
                        </Form.Select>
                      </Col>
                      <Col xs={12} className="mb-3">
                        <label htmlFor="currency">Currency</label>
                        <Form.Select
                          aria-label="Currency"
                          id="currency"
                          disabled={"" != selectedAccount}
                          value={currency}
                          onChange={e => setCurrency(e.target.value)}
                        >
                          {currencyEnums.length > 0 && currencyEnums.map((item, idx) => (
                            <option value={item} key={idx}>{item}</option>
                          ))}
                        </Form.Select>
                      </Col>
                      <Col xs={12} className="mb-3">
                        <label htmlFor="description">Description</label>
                        <Form.Control
                          type="text"
                          id="description"
                          value={description}
                          onChange={e => setDescription(e.target.value)}
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


export default AccountModal;

"use client";

import {
  Box,
  Button,
  FormControl,
  FormLabel,
  Input,
  VStack,
  Card,
  CardBody,
} from "@chakra-ui/react";
import { useState } from "react";

interface AvailabilityFormProps {
  onSearch: (from: string, to: string) => void;
  isLoading: boolean;
}

export function AvailabilityForm({
  onSearch,
  isLoading,
}: AvailabilityFormProps) {
  const [fromDate, setFromDate] = useState("");
  const [toDate, setToDate] = useState("");

  const isFormValid =
    fromDate && toDate && new Date(fromDate) < new Date(toDate);

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    if (isFormValid) {
      onSearch(fromDate, toDate);
    }
  };

  function updateFromDate(event: React.ChangeEvent<HTMLInputElement>) {
    setFromDate(event.target.value);
  }

  function updateToDate(event: React.ChangeEvent<HTMLInputElement>) {
    setToDate(event.target.value);
  }

  const shouldDisableSubmit = !isFormValid || isLoading;

  return (
    <Card variant="outline">
      <CardBody>
        <Box as="form" onSubmit={handleSubmit}>
          <VStack spacing={6} align="stretch">
            <FormControl isRequired>
              <FormLabel>From</FormLabel>
              <Input type="date" value={fromDate} onChange={updateFromDate} />
            </FormControl>

            <FormControl isRequired>
              <FormLabel>To</FormLabel>
              <Input type="date" value={toDate} onChange={updateToDate} />
            </FormControl>

            <Button
              type="submit"
              colorScheme="blue"
              isLoading={isLoading}
              loadingText="Checking..."
              isDisabled={shouldDisableSubmit}
              width="full"
            >
              Check availability
            </Button>
          </VStack>
        </Box>
      </CardBody>
    </Card>
  );
}

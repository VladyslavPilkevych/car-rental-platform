"use client";

import { Box, Flex, HStack, Link as ChakraLink, Text } from "@chakra-ui/react";
import Link from "next/link";
import { usePathname } from "next/navigation";

export function Navbar() {
  const pathname = usePathname();

  return (
    <Box bg="white" px={4} py={4} borderBottomWidth={1} borderColor="gray.100">
      <Flex
        maxW="container.lg"
        mx="auto"
        alignItems="center"
        justify="space-between"
      >
        <ChakraLink as={Link} href="/" _hover={{ textDecoration: "none" }}>
          <Text fontSize="xl" fontWeight="bold" color="blue.600">
            Car Rental
          </Text>
        </ChakraLink>
        <HStack spacing={6}>
          <ChakraLink
            as={Link}
            href="/"
            fontWeight={pathname === "/" ? "bold" : "normal"}
            color={pathname === "/" ? "blue.600" : "gray.600"}
            _hover={{ textDecoration: "none", color: "blue.500" }}
          >
            Home
          </ChakraLink>
          <ChakraLink
            as={Link}
            href="/availability"
            fontWeight={pathname === "/availability" ? "bold" : "normal"}
            color={pathname === "/availability" ? "blue.600" : "gray.600"}
            _hover={{ textDecoration: "none", color: "blue.500" }}
          >
            Availability
          </ChakraLink>
        </HStack>
      </Flex>
    </Box>
  );
}

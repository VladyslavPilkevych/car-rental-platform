# Car Rental Platform - Frontend

This is a [Next.js](https://nextjs.org) project built for a car rental platform.

## Getting Started

First, install the dependencies:

```bash
npm install
```

Then, run the development server:

```bash
npm run dev
```

Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

## Pages

- **`/`** - Personal Home Page / Dashboard
- **`/availability`** - Car Availability Check (allows filtering by date range)

## Libraries

- **[Next.js](https://nextjs.org)** - React framework with App Router
- **[Chakra UI](https://chakra-ui.com)** - Component library for styling
- **[Emotion](https://emotion.sh)** - CSS-in-JS library
- **[Framer Motion](https://www.framer.com/motion/)** - Animation library
- **[TypeScript](https://www.typescriptlang.org/)** - Static type checking

## Project Structure

```text
src/
├── app/                # Application routes and layouts (App Router)
│   ├── availability/   # Availability page components and logic
│   ├── layout.tsx      # Root layout with ChakraProvider
│   └── page.tsx        # Home page
└── shared/             # Shared components, hooks, types, and utils
```

## Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run start` - Start production server
- `npm run lint` - Run ESLint
- `npm run type-check` - Run TypeScript compiler checks
